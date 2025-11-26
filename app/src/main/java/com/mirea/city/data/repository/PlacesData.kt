package com.mirea.city.data.repository

import com.mirea.city.R
import com.mirea.city.data.model.PlacePhoto

val placesByCity = mapOf(
    "moscow" to listOf(
        PlacePhoto(R.drawable.place_red_square, R.string.place_red_square, "red_square"),
        PlacePhoto(R.drawable.place_moscow_kremlin, R.string.place_moscow_kremlin, "moscow_kremlin"),
        PlacePhoto(R.drawable.place_bolshoi_theatre, R.string.place_bolshoi_theatre, "bolshoi_theatre"),
        PlacePhoto(R.drawable.place_gorky_park, R.string.place_gorky_park, "gorky_park")
    ),

    "saint_petersburg" to listOf(
        PlacePhoto(R.drawable.place_hermitage_museum, R.string.place_hermitage, "hermitage"),
        PlacePhoto(R.drawable.place_church_spilled_blood, R.string.place_savior_on_blood, "savior_on_blood"),
        PlacePhoto(R.drawable.place_peterhof_palace, R.string.place_peterhof, "peterhof"),
        PlacePhoto(R.drawable.place_mariinsky_theatre, R.string.place_mariinsky, "mariinsky")
    ),

    "new_york" to listOf(
        PlacePhoto(R.drawable.place_statue_of_liberty, R.string.place_statue_of_liberty, "statue_liberty"),
        PlacePhoto(R.drawable.place_empire_state, R.string.place_empire_state, "empire_state"),
        PlacePhoto(R.drawable.place_times_square, R.string.place_times_square, "times_square"),
        PlacePhoto(R.drawable.place_metropolitan_museum, R.string.place_metropolitan_museum, "metropolitan_museum")
    ),

    "los_angeles" to listOf(
        PlacePhoto(R.drawable.place_walk_of_fame, R.string.place_walk_of_fame, "walk_of_fame"),
        PlacePhoto(R.drawable.place_griffith_observatory, R.string.place_griffith_observatory, "griffith_observatory"),
        PlacePhoto(R.drawable.place_getty_center, R.string.place_getty_center, "getty_center"),
        PlacePhoto(R.drawable.place_universal_studios_hollywood, R.string.place_universal_studios, "universal_studios")
    ),

    "beijing" to listOf(
        PlacePhoto(R.drawable.place_great_wall, R.string.place_great_wall, "great_wall"),
        PlacePhoto(R.drawable.place_forbidden_city, R.string.place_forbidden_city, "forbidden_city"),
        PlacePhoto(R.drawable.place_temple_of_heaven, R.string.place_temple_of_heaven, "temple_of_heaven"),
        PlacePhoto(R.drawable.place_birds_nest, R.string.place_birds_nest, "birds_nest")
    ),

    "shanghai" to listOf(
        PlacePhoto(R.drawable.place_the_bund, R.string.place_the_bund, "the_bund"),
        PlacePhoto(R.drawable.place_shanghai_tower, R.string.place_shanghai_tower, "shanghai_tower"),
        PlacePhoto(R.drawable.place_shanghai_disneyland, R.string.place_shanghai_disneyland, "shanghai_disneyland"),
        PlacePhoto(R.drawable.place_oriental_pearl, R.string.place_oriental_pearl, "oriental_pearl")
    ),

    "london" to listOf(
        PlacePhoto(R.drawable.place_tower_of_london, R.string.place_tower_of_london, "tower_of_london"),
        PlacePhoto(R.drawable.place_buckingham_palace, R.string.place_buckingham_palace, "buckingham_palace"),
        PlacePhoto(R.drawable.place_british_museum, R.string.place_british_museum, "british_museum"),
        PlacePhoto(R.drawable.place_london_eye, R.string.place_london_eye, "london_eye")
    ),

    "edinburgh" to listOf(
        PlacePhoto(R.drawable.place_royal_mile, R.string.place_royal_mile, "royal_mile"),
        PlacePhoto(R.drawable.place_holyrood_palace, R.string.place_holyrood_palace, "holyrood_palace"),
        PlacePhoto(R.drawable.place_national_museum_scotland, R.string.place_national_museum_scotland, "national_museum_scotland"),
        PlacePhoto(R.drawable.place_calton_hill, R.string.place_calton_hill, "calton_hill")
    ),

    "kuala_lumpur" to listOf(
        PlacePhoto(R.drawable.place_batu_caves, R.string.place_batu_caves, "batu_caves"),
        PlacePhoto(R.drawable.place_menara_kuala_lumpur, R.string.place_kl_tower, "kl_tower"),
        PlacePhoto(R.drawable.place_merdeka_square, R.string.place_merdeka_square, "merdeka_square"),
        PlacePhoto(R.drawable.place_suria_klcc, R.string.place_suria_klcc, "suria_klcc")
    ),

    "george_town" to listOf(
        PlacePhoto(R.drawable.place_kek_lok_si, R.string.place_kek_lok_si, "kek_lok_si"),
        PlacePhoto(R.drawable.place_penang_hill, R.string.place_penang_hill, "penang_hill"),
        PlacePhoto(R.drawable.place_clan_jetties, R.string.place_clan_jetties, "clan_jetties"),
        PlacePhoto(R.drawable.place_blue_mansion, R.string.place_blue_mansion, "blue_mansion")
    ),

    "rome" to listOf(
        PlacePhoto(R.drawable.place_vatican_museums, R.string.place_vatican_museums, "vatican_museums"),
        PlacePhoto(R.drawable.place_trevi_fountain, R.string.place_trevi_fountain, "trevi_fountain"),
        PlacePhoto(R.drawable.place_pantheon, R.string.place_pantheon, "pantheon"),
        PlacePhoto(R.drawable.place_spanish_steps, R.string.place_spanish_steps, "spanish_steps")
    ),

    "venice" to listOf(
        PlacePhoto(R.drawable.place_st_marks_square, R.string.place_st_marks_square, "st_marks_square"),
        PlacePhoto(R.drawable.place_st_marks_basilica, R.string.place_st_marks_basilica, "st_marks_basilica"),
        PlacePhoto(R.drawable.place_doges_palace, R.string.place_doges_palace, "doges_palace"),
        PlacePhoto(R.drawable.place_bridge_of_sighs, R.string.place_bridge_of_sighs, "bridge_of_sighs")
    ),

    "dubai" to listOf(
        PlacePhoto(R.drawable.place_dubai_mall, R.string.place_dubai_mall, "dubai_mall"),
        PlacePhoto(R.drawable.place_palm_jumeirah, R.string.place_palm_jumeirah, "palm_jumeirah"),
        PlacePhoto(R.drawable.place_dubai_fountain, R.string.place_dubai_fountain, "dubai_fountain"),
        PlacePhoto(R.drawable.place_dubai_frame, R.string.place_dubai_frame, "dubai_frame")
    ),

    "abu_dhabi" to listOf(
        PlacePhoto(R.drawable.place_sheikh_zayed_mosque, R.string.place_sheikh_zayed_mosque, "sheikh_zayed_mosque"),
        PlacePhoto(R.drawable.place_louvre_abu_dhabi, R.string.place_louvre_abu_dhabi, "louvre_abu_dhabi"),
        PlacePhoto(R.drawable.place_emirates_palace, R.string.place_emirates_palace, "emirates_palace"),
        PlacePhoto(R.drawable.place_yas_island, R.string.place_yas_island, "yas_island")
    ),

    "paris" to listOf(
        PlacePhoto(R.drawable.place_louvre_museum, R.string.place_louvre, "louvre"),
        PlacePhoto(R.drawable.place_notre_dame_cathedral, R.string.place_notre_dame, "notre_dame"),
        PlacePhoto(R.drawable.place_arc_de_triomphe, R.string.place_arc_de_triomphe, "arc_de_triomphe"),
        PlacePhoto(R.drawable.place_champs_elysees, R.string.place_champs_elysees, "champs_elysees")
    ),

    "marseille" to listOf(
        PlacePhoto(R.drawable.place_vieux_port, R.string.place_vieux_port, "vieux_port"),
        PlacePhoto(R.drawable.place_calanques_national_park, R.string.place_calanques, "calanques"),
        PlacePhoto(R.drawable.place_chateau_dif, R.string.place_chateau_dif, "chateau_dif"),
        PlacePhoto(R.drawable.place_mucem, R.string.place_mucem, "mucem")
    )
)

fun getPlacesByCityId(cityId: String) : List<PlacePhoto> {
    return placesByCity[cityId] ?: emptyList()
}