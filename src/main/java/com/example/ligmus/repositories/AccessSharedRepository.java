package com.example.ligmus.repositories;

import com.example.ligmus.data.shared.AccessSharedToken;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccessSharedRepository {

    private List<AccessSharedToken> accessSharedTokens;

    public AccessSharedRepository() {
        this.accessSharedTokens = new ArrayList<>();
    }

    public void save(AccessSharedToken token){
        accessSharedTokens.add(token);
    }

    public List<AccessSharedToken> getAccessSharedTokensForTeacherId(int teacherId) {
        List<AccessSharedToken> tokensList = new ArrayList<>();
        for (AccessSharedToken accessSharedToken : accessSharedTokens) {
            if(accessSharedToken.getGrantedToTeacherId() == teacherId){
                tokensList.add(accessSharedToken);
            }
        }
        return tokensList;
    }

    public List<AccessSharedToken> getAccessSharedtokensByTeacherId(int teacherId) {
        List<AccessSharedToken> tokensList = new ArrayList<>();
        for (AccessSharedToken accessSharedToken : accessSharedTokens) {
            if(accessSharedToken.getGrantedByTeacherId() == teacherId){
                tokensList.add(accessSharedToken);
            }
        }
        return tokensList;
    }
    public List<AccessSharedToken> getAccessSharedtokensToTeacherId(int teacherId) {
        List<AccessSharedToken> tokensList = new ArrayList<>();
        for (AccessSharedToken accessSharedToken : accessSharedTokens) {
            if (accessSharedToken.getGrantedToTeacherId() == teacherId) {
                tokensList.add(accessSharedToken);
            }
        }
        return tokensList;
    }

    public List<AccessSharedToken> getAvalaibleAccessSharedTokensByCurrentUser(int teacherId) {
        List<AccessSharedToken> tokensList = new ArrayList<>();
        for (AccessSharedToken accessSharedToken : accessSharedTokens) {
            if((accessSharedToken.getGrantedByTeacherId() == teacherId) && (accessSharedToken.getExpiresAt()
                                                                            .isAfter(LocalDate.now()))){
                tokensList.add(accessSharedToken);
            }
        }
        return tokensList;
    }
    public List<AccessSharedToken> getAvalaibleAccessSharedTokensToCurrentUser(int teacherId) {
        List<AccessSharedToken> tokensList = new ArrayList<>();
        for (AccessSharedToken accessSharedToken : accessSharedTokens) {
            if((accessSharedToken.getGrantedToTeacherId() == teacherId) && (accessSharedToken.getExpiresAt()
                    .isAfter(LocalDate.now()))){
                tokensList.add(accessSharedToken);
            }
        }
        return tokensList;
    }

    public boolean isTokenExpired(AccessSharedToken accessSharedToken) {
        LocalDate expiresAt = accessSharedToken.getExpiresAt();
        return expiresAt.isBefore(LocalDate.now());
    }

    public boolean chekAccessSharedToken(AccessSharedToken token, int userId) {

        return !isTokenExpired(token) || token.getGrantedToTeacherId() == userId ||
                token.getGrantedByTeacherId() == userId;
    }

    public AccessSharedToken getAccessSharedToken(String token) {
        for (AccessSharedToken accessSharedToken : accessSharedTokens) {
            if(accessSharedToken.getToken().equals(token)){
                return accessSharedToken;
            }
        }
        return null;
    }
}

