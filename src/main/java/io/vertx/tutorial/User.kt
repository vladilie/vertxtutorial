package io.vertx.tutorial

import io.vertx.core.json.JsonObject

data class User(val name:String?, val token: String?, val email:String?, val bio: String?, val image: ByteArray?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (name != other.name) return false
        if (token != other.token) return false
        if (email != other.email) return false
        if (bio != other.bio) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (token?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (bio?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }

    fun asJson() = JsonObject().put("user", JsonObject().put("name", name).put("email", email).put("bio", bio).put("image", image))

}