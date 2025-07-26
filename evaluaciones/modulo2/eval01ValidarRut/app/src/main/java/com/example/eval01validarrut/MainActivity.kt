package com.example.eval01validarrut

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eval01validarrut.ui.theme.Eval01ValidarRutTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Eval01ValidarRutTheme {
                calculo()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState, scope: CoroutineScope) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Verificador de Rut",
                textDecoration = TextDecoration.None,
                fontSize = 30.sp

            )

        },

        navigationIcon = {
            Icon(
                //imageVector = Icons.Filled.ArrowBack,
                //tint = Color(white),
                imageVector = Icons.Filled.Menu,
                contentDescription = "Inicio",
                //Abrir el ModalNavigationDrawer
                //modifier = Modifier.clickable { scope.launch { drawerState.open() } }

            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.Black
        )

    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun calculo(modifier: Modifier = Modifier) {
    var numero1 by remember { mutableStateOf("") }
    var numero2 by remember { mutableStateOf("") }
    var resultado by remember { mutableIntStateOf(0) }
    var textoFinal by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()



        Scaffold(
            topBar = {

                TopBar(drawerState, scope)


            }
        )
        //Fin atributos Scaffold

        {
            //Cuerpo Scaffold:

                innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ){
                //Cuerpo Coluumn:

                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 12.dp),
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    text =
                        // Esta app permite verificar si el Rut ingresado es Valido o no.
                        """                                        
                     Por favor ingrese el Rut en el primer campo y luego el digito verificador en el segundo campo.
                                          
                           Luego presione el boton Verificar.
                                              
                """.trimIndent(),
                )


//                Spacer(
//                    modifier = Modifier.height(16.dp)
//                )


            } //Cierre Column del innerPadding

        } //Cierre cuerpo Scaffold



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        //Central horizontal y verticalmente
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center


    ) {
        //Cuerpo Columna:

        textoEntrada(
            "Ingrese rut sin puntos ni guion y sin digito verificador",
            numero1,
            { numero1 = it })

        textoEntrada(
            "Ingrese digito verificador",
            numero2,
            { numero2 = it })

        Button(onClick = {
            //Invertimos el numero1 que es el rut sin digito verif
            var numero1ComoCadena = numero1.toString()
            //Invertimos el numero1
            var cadenaInvertida = numero1ComoCadena.reversed()
            //Desomponemos la cadenaInvertida en digitos:
            //Creamos la lista digitos de enteros:
            var digitos = mutableListOf<Int>()
            for (caracter in cadenaInvertida) {
                //Los convertimos en int antes de agregarlos a la lista digitos
                digitos.add(caracter.toString().toInt())
            }
            //Recorremos la lista digitos en orden:
            var digito: Int
            var contador = 2
            var producto: Int
            var sumaProd = 0
            for (i in digitos.indices) {
                digito = digitos[i]
                if (contador <= 7) {
                    producto = digito * contador
                    contador++
                } else {
                    contador = 2
                    producto = digito * contador
                    contador++
                }

                sumaProd += producto
            }
            //Obtener la parte entera de la division asi:
            var Division = sumaProd / 11

            var Multipl = Division * 11

            var Resta = abs(Multipl - sumaProd)

            resultado = 11 - Resta

            if (resultado == numero2.toInt()) {
                textoFinal = "El valor ingresasdo SI corresponde a un Rut real"
            } else {
                textoFinal = "El valor ingresasdo NO corresponde a un Rut real"
            }

            //Borrar texto de los TextField:
            numero1 = ""
            numero2 = ""


        })
        //Cierre attr onClick btn Verificar
        {
            //Cuerpo button:
            Icon(
                //Icono +:
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Calcular",
                modifier = Modifier.padding(end = 4.dp)
            )
            //Texto boton:
            Text(
                "Verificar",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 8.dp)
            )


        } //Cierre cuerpo Btn

        //Agregamos un separador horizontal
        HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
        )

        Text(text = textoFinal)

    } //Cierre Column

} //Cierre Composable calculo()

@Composable
fun textoEntrada(
    //Parametros
    title: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    //Componenetes Widget:
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = {
            Text(text = title)

        }
    )
    //Agregamos un separador horizontal
    HorizontalDivider(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 24.dp
        )
    )


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Eval01ValidarRutTheme {
        calculo()
    }
}