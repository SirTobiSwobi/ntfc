package org.SirTobiSwobi.c3.ntfc.resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.ntfc.api.TCConfiguration;
import org.SirTobiSwobi.c3.ntfc.api.TCModel;
import org.SirTobiSwobi.c3.ntfc.db.Configuration;
import org.SirTobiSwobi.c3.ntfc.db.Model;
import org.SirTobiSwobi.c3.ntfc.db.ReferenceHub;
import org.SirTobiSwobi.c3.ntfc.db.SelectionPolicy;
import org.SirTobiSwobi.c3.ntfc.db.WordEmbedding;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

@Path("/model")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActiveModelResource {
	private ReferenceHub refHub;
	private Client client;

	public ActiveModelResource(ReferenceHub refHub, Client client) {
		super();
		this.refHub = refHub;
		this.client = client;
	}
	
	@GET
	public Response getModel(){
		Model model = refHub.getActiveModel();
		if(model==null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}	
		
		TCModel output = ModelResource.buildTCModel(model,refHub);	
		return Response.ok(output).build();
		
	}
	
	@POST
	public Response setActiveModel(TCModel model, @QueryParam("loadFrom") String source){
		if(source!=null&&source.length()>5&&source.startsWith("http")){
			String content = client.target(source).request().get(String.class);
			/*
			Response res = client.target(source).request("application/json").get();
			if(res.getStatusInfo() != Response.Status.OK){
				return Response.status(res.getStatus()).build();
			}else{
				ObjectMapper MAPPER = Jackson.newObjectMapper();
				String content = res.readEntity(String.class);
				try{
					TCModel retrievedModel = MAPPER.readValue(content, TCModel.class);
					Model activeModel = new Model(retrievedModel.getId(), retrievedModel.getConfigurationId(), retrievedModel.getTrainingLog());
					refHub.setActiveModel(activeModel);
				}catch(Exception e){
					return Response.status(404).build();
				}
			}
			*/		
			ObjectMapper MAPPER = Jackson.newObjectMapper();
			try{
				TCModel retrievedModel = MAPPER.readValue(content, TCModel.class);
				
				/*
				WordEmbedding wordEmbedding = new WordEmbedding(retrievedModel.getWordEmbedding().getId(),
																retrievedModel.getWordEmbedding().getSize(), 
																retrievedModel.getWordEmbedding().getDimensions(), 
																retrievedModel.getWordEmbedding().getTerms(), 
																retrievedModel.getWordEmbedding().getVectors());
																*/
				//WordEmbedding wordEmbedding = WordEmbedding.buildFromLocalFile(retrievedModel.getWordEmbedding());
				
				TCConfiguration configuration = retrievedModel.getConfiguration();
				SelectionPolicy selectionPolicy = SelectionPolicy.MicroaverageF1;
				if(configuration.getSelectionPolicy().equals("MicroaverageF1")){
					selectionPolicy=SelectionPolicy.MicroaverageF1;
				}else if(configuration.getSelectionPolicy().equals("MicroaveragePrecision")){
					selectionPolicy=SelectionPolicy.MicroaveragePrecision;
				}else if(configuration.getSelectionPolicy().equals("MicroaverageRecall")){
					selectionPolicy=SelectionPolicy.MicroaverageRecall;
				}else if(configuration.getSelectionPolicy().equals("MacroaverageF1")){
					selectionPolicy=SelectionPolicy.MacroaverageF1;
				}else if(configuration.getSelectionPolicy().equals("MacroaveragePrecision")){
					selectionPolicy=SelectionPolicy.MacroaveragePrecision;
				}else if(configuration.getSelectionPolicy().equals("MacroaverageRecall")){
					selectionPolicy=SelectionPolicy.MacroaverageRecall;
				}
				Configuration conf = new Configuration(configuration.getId(), configuration.getFolds(), configuration.isIncludeImplicits(), configuration.getAssignmentThreshold(),
						selectionPolicy, configuration.getAlgorithm(), configuration.getDistanceMeasure(), configuration.getWordEmbeddingId(), configuration.getApd());
				
				Model activeModel = new Model(retrievedModel.getId(), 
												conf, 
												retrievedModel.getWordEmbedding(),
												retrievedModel.getTrainingLog()
												);
				refHub.setActiveWordEmbedding(WordEmbedding.buildFromLocalFile(retrievedModel.getWordEmbedding()));
				refHub.setActiveModel(activeModel);
			}catch(Exception e){
				return Response.status(404).build();
			}	
			refHub.setNeedsRetraining(false);
			return Response.ok().build();
		}else if(model!=null){
			/*
			WordEmbedding wordEmbedding = new WordEmbedding(model.getWordEmbedding().getId(),
					model.getWordEmbedding().getSize(), 
					model.getWordEmbedding().getDimensions(), 
					model.getWordEmbedding().getTerms(), 
					model.getWordEmbedding().getVectors());
			*/
			/*
			WordEmbedding wordEmbedding;
			try {
				wordEmbedding = WordEmbedding.buildFromLocalFile(model.getWordEmbedding());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.status(404).build();
			}*/
			
			

			TCConfiguration configuration = model.getConfiguration();
			SelectionPolicy selectionPolicy = SelectionPolicy.MicroaverageF1;
			if(configuration.getSelectionPolicy().equals("MicroaverageF1")){
				selectionPolicy=SelectionPolicy.MicroaverageF1;
			}else if(configuration.getSelectionPolicy().equals("MicroaveragePrecision")){
				selectionPolicy=SelectionPolicy.MicroaveragePrecision;
			}else if(configuration.getSelectionPolicy().equals("MicroaverageRecall")){
				selectionPolicy=SelectionPolicy.MicroaverageRecall;
			}else if(configuration.getSelectionPolicy().equals("MacroaverageF1")){
				selectionPolicy=SelectionPolicy.MacroaverageF1;
			}else if(configuration.getSelectionPolicy().equals("MacroaveragePrecision")){
				selectionPolicy=SelectionPolicy.MacroaveragePrecision;
			}else if(configuration.getSelectionPolicy().equals("MacroaverageRecall")){
				selectionPolicy=SelectionPolicy.MacroaverageRecall;
			}
			Configuration conf = new Configuration(configuration.getId(), configuration.getFolds(), configuration.isIncludeImplicits(), configuration.getAssignmentThreshold(),
					selectionPolicy, configuration.getAlgorithm(), configuration.getDistanceMeasure(), configuration.getWordEmbeddingId(), configuration.getApd());
			
			Model activeModel = new Model(model.getId(), 
											conf, 
											model.getWordEmbedding(),
											model.getTrainingLog()					
											);
			try {
				refHub.setActiveWordEmbedding(WordEmbedding.buildFromLocalFile(model.getWordEmbedding()));
			} catch (IOException e) {
				e.printStackTrace();
				return Response.status(400).build();
			}
			refHub.setActiveModel(activeModel);
			refHub.setNeedsRetraining(false);
			return Response.ok().build();
		}else{
			return Response.status(400).build();
		}
		
	
		
	}

	
	@PUT
	public Response updateActiveModel(TCModel model, @QueryParam("loadFrom") String source){
		return setActiveModel(model, source);
	}
}
