package com.miftah.comvis.ui.add

sealed class AddingEvent {
    data class TextFieldMoney(val money : String) : AddingEvent()
    data class TextFieldCount(val count : String) : AddingEvent()
    data class TextFieldDescription(val description : String) : AddingEvent()
    data object SaveToDB : AddingEvent()
}