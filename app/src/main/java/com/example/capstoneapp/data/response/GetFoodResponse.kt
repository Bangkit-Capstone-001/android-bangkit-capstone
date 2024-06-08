package com.example.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

data class GetFoodResponse(

	@field:SerializedName("data")
	val data: List<DataFood?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataFood(

	@field:SerializedName("komposisi_energi_kal")
	val komposisiEnergiKal: Int? = null,

	@field:SerializedName("nama_bahan_makanan")
	val namaBahanMakanan: String? = null,

	@field:SerializedName("komposisi_protein_g")
	val komposisiProteinG: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("komposisi_karbohidrat_g")
	val komposisiKarbohidratG: String? = null,

	@field:SerializedName("komposisi_per")
	val komposisiPer: String? = null,

	@field:SerializedName("komposisi_lemak_g")
	val komposisiLemakG: String? = null
)
