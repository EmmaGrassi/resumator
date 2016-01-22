package io.sytac.resumator.halbuilder;

import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationReader;

/**
 * Custom {@link com.theoryinpractise.halbuilder.api.RepresentationFactory} for supporting Resumator-specific
 * readers and writers.
 */
public class ResumatorJsonRepresentationFactory extends DefaultRepresentationFactory {

    public ResumatorJsonRepresentationFactory() {
        withReader(DefaultRepresentationFactory.HAL_JSON, JsonRepresentationReader.class);
        withRenderer(DefaultRepresentationFactory.HAL_JSON, ResumatorJsonRepresentationWriter.class);

        /**
         * Needed because halbuilder doesn't use {@link ObjectMapper}'s serializationInclusion.
         */
        withFlag(RepresentationFactory.STRIP_NULLS);
    }
}
