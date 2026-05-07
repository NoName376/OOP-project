package infrastructure;

import java.io.Serializable;

public class Request implements Serializable {
    public Request(String requestId, String content) {
        this.requestId = requestId;
        this.content = content;
        this.status = RequestStatus.PENDING;
    }

    public String getRequestId() { return requestId; }
    public String getContent() { return content; }
    public RequestStatus getStatus() { return status; }
    public void updateStatus(RequestStatus newStatus) { this.status = newStatus; }

    @Override
    public String toString() {
        return "Request [" + requestId + ": " + status + "]";
    }

    private String requestId;
    private String content;
    private RequestStatus status;
}
