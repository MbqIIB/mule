package com.laxtech.connector.multipleconf.internal.listener;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.source.EmitsResponse;
import org.mule.runtime.extension.api.runtime.source.Source;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;

import java.io.InputStream;
import java.util.HashMap;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
@Alias("listener")
@EmitsResponse
@MediaType(value = ANY, strict = false)
public class MConfHttpListenerSource  extends Source<InputStream, HashMap> {



@Override
public void onStop() {
	// TODO Auto-generated method stub
	
}

@Override
public void onStart(SourceCallback<InputStream, HashMap> arg0) throws MuleException {
	// TODO Auto-generated method stub
	
}
}
