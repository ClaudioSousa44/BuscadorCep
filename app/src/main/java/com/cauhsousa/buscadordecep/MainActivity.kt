package com.cauhsousa.buscadordecep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cauhsousa.buscadordecep.model.AddressList
import com.cauhsousa.buscadordecep.service.RetrofitFactory
import com.cauhsousa.buscadordecep.ui.theme.BuscadorDeCEPTheme
import com.cauhsousa.buscadordecep.ui.theme.MyPrimaryColor
import com.cauhsousa.buscadordecep.ui.theme.MySecundaryColor
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuscadorDeCEPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SearchCepScreen()
                }
            }
        }
    }
}

@Composable
fun SearchCepScreen() {
    var cepState by remember {
        mutableStateOf("")
    }
    var addresState by remember{
        mutableStateOf(AddressList("","","","",""))
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            color = MyPrimaryColor
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Busca CEP",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = cepState ,
            onValueChange = {cepState = it},
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(text = stringResource(R.string.white_cep))},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MyPrimaryColor,
                unfocusedBorderColor = Black),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Button(
            onClick = {
                val call = RetrofitFactory().getCepService().getCep(cepState)

                call.enqueue(object : retrofit2.Callback<AddressList>{
                    override fun onResponse(
                        call: Call<AddressList>,
                        response: Response<AddressList>
                    ){
                        addresState = response.body()!!
                    }

                    override fun onFailure(call: Call<AddressList>, t: Throwable) {

                    }
                })
                      },
            colors = ButtonDefaults.buttonColors(MyPrimaryColor),
            modifier = Modifier.padding(top = 20.dp),

        ) {
            Text(
                text = stringResource(R.string.button_search),
                color = Color.White
                )
        }

        if(addresState.cep == null){
            Text(text = "cep inválido")
        }else{
            OutlinedTextField(
                value = addresState.logradouro,
                onValueChange = {},
                placeholder = { Text(text = "Logradouro")},
                label = { Text(text = "Logradouro")},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MyPrimaryColor,
                    unfocusedBorderColor = Black)
            )

            OutlinedTextField(
                value = addresState.bairro,
                onValueChange = {},
                placeholder = { Text(text = "Bairro")},
                label = { Text(text = "Bairro")},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MyPrimaryColor,
                    unfocusedBorderColor = Black)
            )
            OutlinedTextField(
                value = addresState.localidade,
                onValueChange = {},
                placeholder = { Text(text = "Cidade")},
                label = { Text(text = "Cidade")},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MyPrimaryColor,
                    unfocusedBorderColor = Black)
            )
            OutlinedTextField(
                value = addresState.uf,
                onValueChange = {},
                placeholder = { Text(text = "Estado")},
                label = { Text(text = "Estado")},
                modifier = Modifier.width(150.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MyPrimaryColor,
                    unfocusedBorderColor = Black)
            )


        }


    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BuscadorDeCEPTheme {
        SearchCepScreen()
    }
}