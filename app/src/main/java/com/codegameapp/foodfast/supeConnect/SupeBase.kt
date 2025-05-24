package com.codegameapp.foodfast.supeConnect

import com.codegameapp.foodfast.BuildConfig
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.createSupabaseClient

object SupeBase {
    val supabase = createSupabaseClient(
        supabaseUrl = BuildConfig.supebaseUrl,
        supabaseKey = BuildConfig.supebaseKey
    ) {
        install(GoTrue)
    }
}