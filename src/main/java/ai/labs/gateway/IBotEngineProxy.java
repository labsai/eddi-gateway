package ai.labs.gateway;

import ai.labs.gateway.models.Context;
import ai.labs.gateway.models.Deployment;
import ai.labs.gateway.models.InputData;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;
import java.util.Map;

@Path("/bots")
public interface IBotEngineProxy {

    @POST
    @Path("/{environment}/{botId}")
    @Operation(description = "Start conversation.")
    void startConversation(@PathParam("environment") Deployment.Environment environment,
                           @PathParam("botId") String botId,
                           @QueryParam("userId") String userId,
                           @Suspended final AsyncResponse asyncResponse);

    @POST
    @Path("/{environment}/{botId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Start conversation with context.")
    void startConversationWithContext(@PathParam("environment") Deployment.Environment environment,
                                      @PathParam("botId") String botId,
                                      @QueryParam("userId") String userId, Map<String, Context> context,
                                      @Suspended final AsyncResponse asyncResponse);

    @POST
    @Path("/{conversationId}/endConversation")
    @Operation(description = "End conversation.")
    void endConversation(@PathParam("conversationId") String conversationId,
                         @Suspended final AsyncResponse asyncResponse);

    @GET
    @Path("/{conversationId}/log")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Read conversation. outputType=text || json")
    void readConversationLog(@PathParam("conversationId") String conversationId,
                             @QueryParam("outputType") @DefaultValue("json") String outputType,
                             @QueryParam("logSize") @DefaultValue("-1") Integer logSize,
                             @Suspended final AsyncResponse asyncResponse);

    @GET
    @Path("/{environment}/{botId}/{conversationId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Read conversation.")
    void readConversation(@PathParam("environment") Deployment.Environment environment,
                          @PathParam("botId") String botId,
                          @PathParam("conversationId") String conversationId,
                          @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                          @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                          @QueryParam("returningFields") List<String> returningFields,
                          @Suspended final AsyncResponse asyncResponse);

    @GET
    @Path("/{environment}/conversationstatus/{conversationId}")
    @Operation(description = "Get conversation state.")
    void getConversationState(@PathParam("environment") Deployment.Environment environment,
                              @PathParam("conversationId") String conversationId,
                              @Suspended final AsyncResponse asyncResponse);

    @POST
    @Path("/{environment}/{botId}/{conversationId}/rerun")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Read conversation.")
    void rerunLastConversationStep(@PathParam("environment") Deployment.Environment environment,
                                   @PathParam("botId") String botId,
                                   @PathParam("conversationId") String conversationId,
                                   @QueryParam("language") String language,
                                   @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                                   @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                                   @QueryParam("returningFields") List<String> returningFields,
                                   @Suspended final AsyncResponse asyncResponse);

    @POST
    @Path("/{environment}/{botId}/{conversationId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Talk to bot.")
    void say(@PathParam("environment") Deployment.Environment environment,
             @PathParam("botId") String botId,
             @PathParam("conversationId") String conversationId,
             @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
             @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
             @QueryParam("returningFields") List<String> returningFields,
             @DefaultValue("") String message,
             @Suspended final AsyncResponse asyncResponse);

    @POST
    @Path("/{environment}/{botId}/{conversationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Talk to bot with context.")
    void sayWithinContext(@PathParam("environment") Deployment.Environment environment,
                          @PathParam("botId") String botId,
                          @PathParam("conversationId") String conversationId,
                          @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                          @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                          @QueryParam("returningFields") List<String> returningFields,
                          InputData inputData,
                          @Suspended final AsyncResponse asyncResponse);

    @GET
    @Path("/{environment}/{botId}/undo/{conversationId}")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(description = "Is UNDO available?")
    void isUndoAvailable(@PathParam("environment") Deployment.Environment environment,
                         @PathParam("botId") String botId,
                         @PathParam("conversationId") String conversationId,
                         @Suspended final AsyncResponse asyncResponse);

    @POST
    @Path("/{environment}/{botId}/undo/{conversationId}")
    @Operation(description = "UNDO last conversation step.")
    void undo(@PathParam("environment") Deployment.Environment environment,
              @PathParam("botId") String botId,
              @PathParam("conversationId") String conversationId,
              @Suspended final AsyncResponse asyncResponse);

    @GET
    @Path("/{environment}/{botId}/redo/{conversationId}")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(description = "Is REDO available?")
    void isRedoAvailable(@PathParam("environment") Deployment.Environment environment,
                         @PathParam("botId") String botId,
                         @PathParam("conversationId") String conversationId,
                         @Suspended final AsyncResponse asyncResponse);

    @POST
    @Path("/{environment}/{botId}/redo/{conversationId}")
    @Operation(description = "REDO last conversation step.")
    void redo(@PathParam("environment") Deployment.Environment environment,
              @PathParam("botId") String botId,
              @PathParam("conversationId") String conversationId,
              @Suspended final AsyncResponse asyncResponse);
}

