package ai.labs.gateway;

import ai.labs.gateway.models.InputData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.AsyncResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class BotManagementProxy implements IBotManagementProxy {
    @Inject
    @RestClient
    BotManagementService botManagementService;

    private static final Logger LOGGER = Logger.getLogger(BotManagementProxy.class);

    @Override
    public void proxyLoadConversationMemory(String intent,
                                     String userId,
                                     String language,
                                     Boolean returnCurrentStepOnly,
                                     AsyncResponse response) {
        botManagementService.loadConversationMemory(intent, userId, language, false, returnCurrentStepOnly, null)
                .whenComplete((simpleConversationMemorySnapshot, throwable) -> {
                    if (throwable != null) {
                        BotManagementProxy.LOGGER.error(throwable.getMessage(), throwable);
                        response.resume(throwable);
                    } else {
                        response.resume(simpleConversationMemorySnapshot);
                    }
                });
    }

    @Override
    public void proxySayWithinContext(String intent,
                                      String userId,
                                      Boolean returnDetailed,
                                      Boolean returnCurrentStepOnly,
                                      List<String> returningFields,
                                      InputData inputData,
                                      final AsyncResponse response) {
        botManagementService.sayWithinContext(intent, userId, returnDetailed, returnCurrentStepOnly, returningFields, inputData)
                .whenComplete((simpleConversationMemorySnapshot, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error(throwable.getMessage(), throwable);
                        response.resume(throwable);
                    } else {
                        response.resume(simpleConversationMemorySnapshot);
                    }
                });
    }

    @Override
    public void proxyEndCurrentConversation(String intent,
                                            String userId,
                                            final AsyncResponse response) {
        botManagementService.endCurrentConversation(intent, userId)
                .whenComplete((responseValue, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error(throwable.getMessage(), throwable);
                        response.resume(throwable);
                    } else {
                        response.resume(responseValue);
                    }
                });
    }

    @Override
    public void proxyIsUndoAvailable(String intent,
                                     String userId,
                                     final AsyncResponse response) {
        botManagementService.isUndoAvailable(intent, userId)
                .whenComplete((isAvailable, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error(throwable.getMessage(), throwable);
                        response.resume(throwable);
                    } else {
                        response.resume(isAvailable);
                    }
                });
    }

    @Override
    public void proxyUndo(String intent,
                          String userId,
                          final AsyncResponse response) {
        botManagementService.undo(intent, userId)
                .whenComplete((responseValue, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error(throwable.getMessage(), throwable);
                        response.resume(throwable);
                    } else {
                        response.resume(responseValue);
                    }
                });
    }

    @Override
    public void proxyIsRedoAvailable(String intent,
                                     String userId,
                                     final AsyncResponse response) {
        botManagementService.isRedoAvailable(intent, userId)
                .whenComplete((isAvailable, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error(throwable.getMessage(), throwable);
                        response.resume(throwable);
                    } else {
                        response.resume(isAvailable);
                    }
                });
    }

    @Override
    public void proxyRedo(String intent,
                          String userId,
                          final AsyncResponse response) {
        botManagementService.redo(intent, userId)
                .whenComplete((responseValue, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error(throwable.getMessage(), throwable);
                        response.resume(throwable);
                    } else {
                        response.resume(responseValue);
                    }
                });
    }
}
