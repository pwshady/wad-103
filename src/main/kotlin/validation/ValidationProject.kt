package validation

import java.util.*


class ValidationProject {
    companion object {
        fun nameValidation(text : String) : Pair<String, Int>
        {
            val regex = "^[A-Za-z0-9]*$".toRegex()
            if (text == ""){
                return Pair("The project name field should not be empty\n",2)
            } else if (!regex.matches(text)) {
                return Pair("The project name field should consist only of A-Za-z0-9\n",2)
            } else{
                return Pair("",0)
            }
        }
        fun domenNameValidation(text : String) : Pair<String, Int>
        {
            val regex = "^(?!https?).*$".toRegex()
            if (text == ""){
                return Pair("The domen name field should not be empty\n",2)
            } else if (!regex.matches(text)) {
                return Pair("The domen name field must consist of a url without a protocol, www is not required\n",2)
            } else{
                return Pair("",0)
            }
        }
        fun dateTimeValidation(text: String, min : String, max : String, fildName : String) : Pair<String, Int>
        {
            val regex = "^[0-9].*$".toRegex()
            if (text == ""){
                return Pair("The ${fildName} field should not be empty\n",2)
            } else if (!regex.matches(text)) {
                return Pair("The field should consist only of 0-9\n",2)
            } else{
                if (text.length > 14){
                    return Pair("The field can contain a maximum of 14 characters\n",1)
                } else{
                    if (min != ""){
                        if (text.padEnd(14,'0') < min){
                            return Pair("Date and time less than minimum ${min}\n",1)
                        } else{
                            return Pair("",0)
                        }
                    }
                    if (max != ""){
                        if (text.padEnd(14,'0') > max){
                            return Pair("Date and time greater than the maximum ${max}\n",1)
                        } else{
                            return Pair("",0)
                        }
                    }
                }
                return Pair("",0)
            }
        }
        fun directotyValidation(text : String) : Pair<String,Int>
        {
            val regex = "^[A-Za-z0-9:]*\$".toRegex()
            if (text == ""){
                return Pair("The directory field should not be empty\n",2)
            } else if (!regex.matches(text.replace("\\",""))) {
                return Pair("Invalid directory path\n",2)
            } else {
                return Pair("",0)
            }
        }
    }
}