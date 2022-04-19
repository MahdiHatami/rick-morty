package com.metis.rickmorty.di.scope

import javax.inject.Qualifier

/**
 * Since all of our dispatchers share the same type, CoroutineDispatcher,
 * we need to help Dagger to distinguish between them and
 * @Qualifier is an ideal Dagger construct for that.
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher
