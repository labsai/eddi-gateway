package ai.labs.gateway;

import ai.labs.gateway.models.Context;
import ai.labs.gateway.models.Deployment;
import ai.labs.gateway.models.InputData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.AsyncResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class BotEngineProxy implements IBotEngineProxy {
    @Inject
    @RestClient
    BotEngineService botEngineService;

    private static final Logger LOGGER = Logger.getLogger(BotEngineProxy.class);

    @Override
    public void startConversation(Deployment.Environment environment, String botId, String userId, AsyncResponse response) {
        botEngineService.startConversation(environment, botId, userId)
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
    public void startConversationWithContext(Deployment.Environment environment, String botId, String userId, Map<String, Context> context, AsyncResponse response) {
        botEngineService.startConversationWithContext(environment, botId, userId, context)
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
    public void endConversation(String conversationId, AsyncResponse response) {
        botEngineService.endConversation(conversationId)
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
    public void readConversationLog(String conversationId, String outputType, Integer logSize, AsyncResponse response) {
        botEngineService.readConversationLog(conversationId, outputType, logSize)
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
    public void readConversation(Deployment.Environment environment, String botId, String conversationId, Boolean returnDetailed, Boolean returnCurrentStepOnly, List<String> returningFields, AsyncResponse response) {
        botEngineService.readConversation(environment, botId, conversationId, returnDetailed, returnCurrentStepOnly, returningFields)
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
    public void getConversationState(Deployment.Environment environment, String conversationId, AsyncResponse response) {
        botEngineService.getConversationState(environment, conversationId)
                .whenComplete((conversationState, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error(throwable.getMessage(), throwable);
                        response.resume(throwable);
                    } else {
                        response.resume(conversationState);
                    }
                });
    }

    @Override
    public void rerunLastConversationStep(Deployment.Environment environment, String botId, String conversationId, String language, Boolean returnDetailed, Boolean returnCurrentStepOnly, List<String> returningFields, AsyncResponse response) {
        botEngineService.rerunLastConversationStep(environment, botId, conversationId, language, returnDetailed, returnCurrentStepOnly, returningFields)
                .whenComplete((unused, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error(throwable.getMessage(), throwable);
                        response.resume(throwable);
                    } else {
                        response.resume(unused);
                    }
                });
    }

    @Override
    public void say(Deployment.Environment environment, String botId, String conversationId, Boolean returnDetailed, Boolean returnCurrentStepOnly, List<String> returningFields, String message, AsyncResponse response) {
        botEngineService.say(environment, botId, conversationId, returnDetailed, returnCurrentStepOnly, returningFields, message)
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
    public void sayWithinContext(Deployment.Environment environment, String botId, String conversationId, Boolean returnDetailed, Boolean returnCurrentStepOnly, List<String> returningFields, InputData inputData, AsyncResponse response) {
        botEngineService.sayWithinContext(environment, botId, conversationId, returnDetailed, returnCurrentStepOnly, returningFields, inputData)
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
    public void isUndoAvailable(Deployment.Environment environment, String botId, String conversationId, AsyncResponse response) {
        botEngineService.isUndoAvailable(environment, botId, conversationId)
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
    public void undo(Deployment.Environment environment, String botId, String conversationId, AsyncResponse response) {
        botEngineService.undo(environment, botId, conversationId)
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
    public void isRedoAvailable(Deployment.Environment environment, String botId, String conversationId, AsyncResponse response) {
        botEngineService.isRedoAvailable(environment, botId, conversationId)
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
    public void redo(Deployment.Environment environment, String botId, String conversationId, AsyncResponse response) {
        botEngineService.redo(environment, botId, conversationId)
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
