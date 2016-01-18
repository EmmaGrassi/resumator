package io.sytac.resumator.docx;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.poi.xwpf.usermodel.*;

import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * This class generates docx files based on a template and placeholder mappings.
 *
 * @author Jack Tol
 * @since 0.1
 */
public class DocxGenerator {

    private static final Pattern placeholderPattern = Pattern.compile(Pattern.quote("${") + "[A-Za-z0-9\\.]+" + Pattern.quote("}"));

    /**
     * Generates a docx {@link StreamingOutput} based on a template and placeholder mappings.
     * @param templateStream the stream of the template to base the docx {@link StreamingOutput} on
     * @return a {@link StreamingOutput} with the generated docx
     * @throws IOException
     */
    public StreamingOutput generate(InputStream templateStream, Map<String, String> replacementMap) throws IOException {
        XWPFDocument doc = new XWPFDocument(templateStream);

        doc.getTables().forEach(table -> {
            replacePlaceholders(table, replacementMap);
            removeTableRowsWithPlaceholdersLeft(table);
        });

        return out -> {
            doc.write(out);
            doc.close();
        };
    }

    private void replacePlaceholders(XWPFTable table, Map<String, String> replacementMap) {
        getCellsWithPlaceholders(table)
                .flatMap(cell -> cell.getParagraphs().stream())
                .forEach(paragraph -> replacePlaceholdersInParagraph(paragraph, replacementMap));
    }

    private void removeTableRowsWithPlaceholdersLeft(XWPFTable table) {
        List<XWPFTableRow> toRemove = getCellsWithPlaceholders(table)
                .map(XWPFTableCell::getTableRow)
                .distinct()
                .collect(toList());

        toRemove.forEach(row -> {
                    int rowIdx = row.getTable().getRows().indexOf(row);
                    row.getTable().removeRow(rowIdx);
                });
    }

    private void replacePlaceholdersInParagraph(XWPFParagraph paragraph, Map<String, String> replacementMap) {
        String text = paragraph.getText();

        // Word splits up a single piece of text into multiple runs in tables, so let's just delete those and create a single run instead.
        while (!paragraph.getRuns().isEmpty()) {
            paragraph.removeRun(0);
        }

        paragraph.createRun().setText(StrSubstitutor.replace(text, replacementMap));
    }

    private boolean hasPlaceholder(String text) {
        return placeholderPattern.matcher(text).find();
    }

    private Stream<XWPFTableCell> getCellsWithPlaceholders(XWPFTable table) {
        return table.getRows().stream()
                .flatMap(row -> row.getTableCells().stream())
                .filter(cell -> hasPlaceholder(cell.getText()));
    }
}
