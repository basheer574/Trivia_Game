package com.example.triviagame.data.domain.repository

import com.example.triviagame.data.domain.Status
import com.example.triviagame.data.domain.TriviaResponseMain
import com.example.triviagame.data.domain.network.Client
import io.reactivex.rxjava3.core.Observable


object TrivaRepository {
    fun getTrivia(
        qNumber: String,
        category: String,
        difficulty: String,
        type: String
    ): Observable<Status<TriviaResponseMain>> {

        Observable.create { emitter ->
            emitter.onNext(
                Client.getTrivia(
                    qNumber,
                    category,
                    difficulty,
                    type
                )
            ) //to make it easier we pick the first city and skip others

            emitter.onComplete()
        }


    }
}