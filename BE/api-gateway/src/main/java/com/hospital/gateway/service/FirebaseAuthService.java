package com.hospital.gateway.service;

import com.google.firebase.auth.FirebaseAuth;
import com.hospital.gateway.model.FirebaseUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class FirebaseAuthService {

    public Mono<FirebaseUser> verifyToken(String token) {
        return Mono.fromCallable(() -> FirebaseAuth.getInstance().verifyIdToken(token))
                .subscribeOn(Schedulers.boundedElastic())
                .map(firebaseToken -> new FirebaseUser(
                        firebaseToken.getUid(),
                        firebaseToken.getEmail(),
                        (String) firebaseToken.getClaims().get("role")));
    }
}
