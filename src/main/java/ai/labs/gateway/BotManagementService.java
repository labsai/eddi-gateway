package ai.labs.gateway;

import ai.labs.gateway.models.InputData;
import ai.labs.gateway.models.SimpleConversationMemorySnapshot;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.concurrent.CompletionStage;

@Path("/managedbots")
@RegisterRestClient(configKey = "bot-api")
public interface BotManagementService {
    @GET
    @Path("/{intent}/{userId}")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout(60000)
    @Operation(description = "Read conversation.")
    CompletionStage<SimpleConversationMemorySnapshot> loadConversationMemory(@PathParam("intent") String intent,
                                                                             @PathParam("userId") String userId,
                                                                             @QueryParam("language") String language,
                                                                             @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                                                                             @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                                                                             @QueryParam("returningFields") List<String> returningFields);


    @POST
    @Path("/{intent}/{userId}")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Talk to bot with context.")
    CompletionStage<SimpleConversationMemorySnapshot> sayWithinContext(@PathParam("intent") String intent,
                                                                       @PathParam("userId") String userId,
                                                                       @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                                                                       @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                                                                       @QueryParam("returningFields") List<String> returningFields,
                                                                       InputData inputData);

    @POST
    @Path("/{intent}/{userId}/endConversation")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Operation(description = "End conversation.")
    CompletionStage<Response> endCurrentConversation(@PathParam("intent") String intent, @PathParam("userId") String userId);

    @GET
    @Path("/{intent}/{userId}/undo")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(description = "Is UNDO available?")
    CompletionStage<Boolean> isUndoAvailable(@PathParam("intent") String intent,
                                             @PathParam("userId") String userId);

    @POST
    @Path("/{intent}/{userId}/undo")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Operation(description = "UNDO last conversation step.")
    CompletionStage<Response> undo(@PathParam("intent") String intent,
                                   @PathParam("userId") String userId);

    @GET
    @Path("/{intent}/{userId}/redo")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(description = "Is REDO available?")
    CompletionStage<Boolean> isRedoAvailable(@PathParam("intent") String intent,
                                             @PathParam("userId") String userId);

    @POST
    @Path("/{intent}/{userId}/redo")
    @Retry(maxRetries = 4, delay = 1000, jitter = 200, maxDuration = 8000)
    @Operation(description = "REDO last conversation step.")
    CompletionStage<Response> redo(@PathParam("intent") String intent,
                                   @PathParam("userId") String userId);
}
