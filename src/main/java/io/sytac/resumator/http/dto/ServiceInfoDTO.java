package io.sytac.resumator.http.dto;

import com.theoryinpractise.halbuilder.api.Link;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Service document transfer object
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class ServiceInfoDTO {

    private final String appName;
    private final String version;
    private final Collection<Link> links;

    public ServiceInfoDTO(String appName, String version, Collection<Link> links) {
        this.appName = appName;
        this.version = version;
        this.links = links;
    }

    public String getAppName() {
        return appName;
    }

    public String getVersion() {
        return version;
    }

    public Collection<Link> getLinks() {
        return Collections.unmodifiableCollection(links);
    }

    public Optional<Link> getLinkByRel(String rel) {
        return links.stream().filter(link -> link.getRel().endsWith(rel)).findFirst();
    }
}
