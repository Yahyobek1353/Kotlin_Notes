package com.example.kotlinnotes

import java.io.Serializable


class Note(title: String?, description: String?, date: String?, image: Int) : Serializable {
    var title: String? = title
        private set
    var description: String? = description
        private set
    var date: String? = date
        private set
    var image = image
        private set


}
