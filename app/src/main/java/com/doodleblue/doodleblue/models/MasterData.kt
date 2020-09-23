package com.doodleblue.doodleblue.models

data class MasterData(
    val data:List<data>
)
data class data(
    val id:String,
    val rank:String,
    val name:String,
    val priceUsd:String,
    val changePercent24Hr:String,
    val marketCapUsd:String
)