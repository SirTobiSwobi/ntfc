package org.SirTobiSwobi.c3.ntfc.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.SirTobiSwobi.c3.ntfc.NtfcConfiguration;
import org.SirTobiSwobi.c3.ntfc.api.TCMetadata;

@Path("/metadata")
@Produces(MediaType.APPLICATION_JSON)
public class MetadataResource {
	private NtfcConfiguration configuration;
	
	public MetadataResource(){
		super();
	}
	
	public MetadataResource(NtfcConfiguration configuration){
		this.configuration = configuration;
	}
	
	@GET
    @Timed
	public TCMetadata getMetadata(){
		TCMetadata metadata = new TCMetadata(
				configuration.getName(),
				configuration.getCalls(),
				configuration.getAlgorithm(),
				configuration.getPhases(),
				configuration.getAlgorithm(),
				configuration.getConfiguration(),
				configuration.getConfigOptions(),
				configuration.getArchetype(),
				configuration.getRunType(),
				configuration.getDebugExamples()
				);
		
		return metadata;
		
	}
	
}
