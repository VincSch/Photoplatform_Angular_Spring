/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto;

/**
 * Response message data.
 *
 * @author Sergej Meister
 */
public class ResponseMessageData {

    protected Long code;

    protected String messageSuccess;

    protected String messageFailed;

    /**
     * returns Response message code.
     * @return message code.
     */
    public Long getCode() {
        return code;
    }

    /**
     * Sets response message code.
     *
     * @param code message code.
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * Returns success message.
     *
     * @return success message.
     */
    public String getMessageSuccess() {
        return messageSuccess;
    }

    /**
     * Sets success message.
     * @param messageSuccess success message.
     */
    public void setMessageSuccess(String messageSuccess) {
        this.messageSuccess = messageSuccess;
    }

    /**
     * Returns failed message.
     * @return failed message.
     */
    public String getMessageFailed() {
        return messageFailed;
    }

    /**
     * Sets failed message.
     * @param messageFailed failed message.
     */
    public void setMessageFailed(String messageFailed) {
        this.messageFailed = messageFailed;
    }
}
