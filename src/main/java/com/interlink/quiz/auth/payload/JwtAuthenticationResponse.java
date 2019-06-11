package com.interlink.quiz.auth.payload;

public class JwtAuthenticationResponse {

    private String accessToken;
    private boolean isCurator;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken, boolean isCurator) {
        this.accessToken = accessToken;
        this.isCurator = isCurator;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isCurator() {
        return isCurator;
    }

    public void setCurator(boolean curator) {
        isCurator = curator;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
