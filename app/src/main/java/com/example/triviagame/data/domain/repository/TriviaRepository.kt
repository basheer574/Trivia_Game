package com.example.triviagame.data.domain.repository

import com.example.triviagame.data.domain.Status
import com.example.triviagame.data.domain.data.TriviaResponseMain
import com.example.triviagame.data.domain.network.Client
import io.reactivex.rxjava3.core.Observable


object TriviaRepository {
     fun getTrivia(qNumber: String,
                   category: String,
                   difficulty: String,
                   type: String):  Observable<Status<TriviaResponseMain>>{
        return Observable.create { emitter ->
            emitter.onNext(Status.Loading)
            emitter.onNext(Client.getTriviaResponse(qNumber,category,difficulty,type))
            emitter.onComplete()
        }
    }
}