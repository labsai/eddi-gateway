package ai.labs.gateway;

import ai.labs.gateway.models.InputData;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Path("/managedbots")
public interface IBotManagementProxy {

    @GET
    @Path("/{intent}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Read conversation.")
    void proxyLoadConversationMemory(@PathParam("intent") String intent,
                                     @PathParam("userId") String userId,
                                     @QueryParam("language") String language,
                                     @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                                     @Suspended AsyncResponse response);

    @POST
    @Path("/{intent}/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Talk to bot with context.")
    void proxySayWithinContext(@PathParam("intent") String intent,
                               @PathParam("userId") String userId,
                               @QueryParam("returnDetailed") @DefaultValue("false") Boolean returnDetailed,
                               @QueryParam("returnCurrentStepOnly") @DefaultValue("true") Boolean returnCurrentStepOnly,
                               @QueryParam("returningFields") List<String> returningFields,
                               InputData inputData,
                               @Suspended AsyncResponse response);

    @POST
    @Path("/{intent}/{userId}/endConversation")
    @Operation(description = "End conversation.")
    void proxyEndCurrentConversation(@PathParam("intent") String intent,
                                     @PathParam("userId") String userId,
                                     @Suspended AsyncResponse response);

    @GET
    @Path("/{intent}/{userId}/undo")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(description = "Is UNDO available?")
    void proxyIsUndoAvailable(@PathParam("intent") String intent,
                              @PathParam("userId") String userId,
                              @Suspended AsyncResponse response);

    @POST
    @Path("/{intent}/{userId}/undo")
    @Operation(description = "UNDO last conversation step.")
    void proxyUndo(@PathParam("intent") String intent,
                   @PathParam("userId") String userId,
                   @Suspended AsyncResponse response);

    @GET
    @Path("/{intent}/{userId}/redo")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(description = "Is REDO available?")
    void proxyIsRedoAvailable(@PathParam("intent") String intent,
                              @PathParam("userId") String userId,
                              @Suspended AsyncResponse response);

    @POST
    @Path("/{intent}/{userId}/redo")
    @Operation(description = "REDO last conversation step.")
    void proxyRedo(@PathParam("intent") String intent,
                   @PathParam("userId") String userId,
                   @Suspended AsyncResponse response);
}
