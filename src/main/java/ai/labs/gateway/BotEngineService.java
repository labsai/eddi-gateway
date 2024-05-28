package ai.labs.gateway;

import ai.labs.gateway.models.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

@Path("/bots")
@RegisterRestClient(configKey = "bot-api")
public interface BotEngineService {

    @POST
    @Path("/{environment}/{botId}")
    @Operation(description = "Start conversation.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<Response> startConversation(@PathParam("environment") Deployment.Environment environment,
                                                @PathParam("botId") String botId,
                                                @QueryParam("userId") String userId);

    @POST
    @Path("/{environment}/{botId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Start conversation with context.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<Response> startConversationWithContext(@PathParam("environment") Deployment.Environment environment,
                                                           @PathParam("botId") String botId,
                                                           @QueryParam("userId") String userId, Map<String, Context> context);

    @POST
    @Path("/{conversationId}/endConversation")
    @Operation(description = "End conversation.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<Void> endConversation(@PathParam("conversationId") String conversationId);

    @GET
    @Path("/{conversationId}/log")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Read conversation. outputType=text || json")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<SimpleConversationMemorySnapshot> readConversationLog(@PathParam("conversationId") String conversationId,
                                                  @QueryParam("outputType") @DefaultValue("json") String outputType,
                                                  @QueryParam("logSize") @DefaultValue("-1") Integer logSize);

    @GET
    @Path("/{environment}/{botId}/{conversationId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Read conversation.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<SimpleConversationMemorySnapshot> readConversation(@PathParam("environment") Deployment.Environment environment,
                                                                       @PathParam("botId") String botId,
                                                                       @PathParam("conversationId") String conversationId,
                                                                       @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                                                                       @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                                                                       @QueryParam("returningFields") List<String> returningFields);

    @GET
    @Path("/{environment}/conversationstatus/{conversationId}")
    @Operation(description = "Get conversation state.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<ConversationState> getConversationState(@PathParam("environment") Deployment.Environment environment,
                                                            @PathParam("conversationId") String conversationId);

    @POST
    @Path("/{environment}/{botId}/{conversationId}/rerun")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Read conversation.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<SimpleConversationMemorySnapshot> rerunLastConversationStep(@PathParam("environment") Deployment.Environment environment,
                                                    @PathParam("botId") String botId,
                                                    @PathParam("conversationId") String conversationId,
                                                    @QueryParam("language") String language,
                                                    @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                                                    @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                                                    @QueryParam("returningFields") List<String> returningFields);

    @POST
    @Path("/{environment}/{botId}/{conversationId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Talk to bot.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<SimpleConversationMemorySnapshot> say(@PathParam("environment") Deployment.Environment environment,
                              @PathParam("botId") String botId,
                              @PathParam("conversationId") String conversationId,
                              @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                              @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                              @QueryParam("returningFields") List<String> returningFields,
                              @DefaultValue("") String message);

    @POST
    @Path("/{environment}/{botId}/{conversationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Talk to bot with context.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<SimpleConversationMemorySnapshot> sayWithinContext(@PathParam("environment") Deployment.Environment environment,
                                           @PathParam("botId") String botId,
                                           @PathParam("conversationId") String conversationId,
                                           @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                                           @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                                           @QueryParam("returningFields") List<String> returningFields,
                                           InputData inputData);

    @GET
    @Path("/{environment}/{botId}/undo/{conversationId}")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(description = "Is UNDO available?")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<Boolean> isUndoAvailable(@PathParam("environment") Deployment.Environment environment,
                                             @PathParam("botId") String botId,
                                             @PathParam("conversationId") String conversationId);

    @POST
    @Path("/{environment}/{botId}/undo/{conversationId}")
    @Operation(description = "UNDO last conversation step.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<Response> undo(@PathParam("environment") Deployment.Environment environment,
                                   @PathParam("botId") String botId,
                                   @PathParam("conversationId") String conversationId);

    @GET
    @Path("/{environment}/{botId}/redo/{conversationId}")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(description = "Is REDO available?")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<Boolean> isRedoAvailable(@PathParam("environment") Deployment.Environment environment,
                                             @PathParam("botId") String botId,
                                             @PathParam("conversationId") String conversationId);

    @POST
    @Path("/{environment}/{botId}/redo/{conversationId}")
    @Operation(description = "REDO last conversation step.")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Timeout(60000)
    CompletionStage<Response> redo(@PathParam("environment") Deployment.Environment environment,
                                   @PathParam("botId") String botId,
                                   @PathParam("conversationId") String conversationId);
}

