package com.example.xp.ahorcado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String palabraOculta = "CETYS";
    int numeroFallos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.ventanaJuego,                   //El manager maneja todas los frajmento  y la trasaccion dice que empieze a crear
                    new VentanaAhorcado()).commit();                                                //El commit es para que lanze la accion
        }
        palabraOculta = escogePalabra();
    }

    @Override
    protected void onStart() {
        super.onStart();
        palabraOculta = escogePalabra();
        String barras = "";
        for (int i = 0; i < palabraOculta.length(); i++) {
            barras += "_ ";
            ((TextView) findViewById(R.id.palabraConGuiones)).setText(barras);
        }
    }

    public void botonPulsado(View vista) {
        Button boton = (Button) findViewById(vista.getId());
        boton.setVisibility(View.INVISIBLE);
        chequeaLetra(boton.getText().toString());
    }

    public void botonPulsadoRestart(View vista) {
        Button boton = (Button) findViewById(vista.getId());
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void chequeaLetra(String _letra)
    {
        /*this.findViewById(R.id.layout5).setVisibility(View.INVISIBLE);*/
        if (numeroFallos < 6) {
            _letra = _letra.toUpperCase();

            ImageView imagenAhorcado = ((ImageView) findViewById(R.id.imagenAhorcado));                 //Esto es un outler
            TextView textoGuiones = ((TextView) findViewById(R.id.palabraConGuiones));
            String palabraConGuiones = textoGuiones.getText().toString();
            boolean acierto = false;

            for (int i = 0; i < palabraOculta.length(); i++) {
                if (palabraOculta.charAt(i) == _letra.charAt(0)) {
                    //quita el guión bajo de la letra correspondiente
                    palabraConGuiones = palabraConGuiones.substring(0, 2 * i) + _letra
                            + palabraConGuiones.substring(2 * i + 1);
                    acierto = true;
                }
            }
            if (!palabraConGuiones.contains("_"))                                                    //chequeo si se a terminado la partida porque ha acertado todas las letras
            {

                imagenAhorcado.setImageResource(R.drawable.acertastetodo);
                this.findViewById(R.id.layout1).setVisibility(View.INVISIBLE);
                this.findViewById(R.id.layout2).setVisibility(View.INVISIBLE);
                this.findViewById(R.id.layout3).setVisibility(View.INVISIBLE);
                this.findViewById(R.id.layout4).setVisibility(View.INVISIBLE);
                this.findViewById(R.id.layout5).setVisibility(View.VISIBLE);
            }

            textoGuiones.setText(palabraConGuiones);

            if (!acierto) {
                numeroFallos++;
                switch (numeroFallos) {
                    case 0:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_0);
                        break;
                    case 1:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_1);
                        break;
                    case 2:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_2);
                        break;
                    case 3:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_3);
                        break;
                    case 4:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_4);
                        break;
                    case 5:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_5);
                        break;
                    default:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_fin);
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        break;
                }
            }
        }
    }

    private String escogePalabra() {
        String auxiliar = "CETYS";
        String[] listaPalabra = {"CETYS", "hola", "adios", "desconocido", "icono", "diacono", "conocida", "conocimiento"};
        Random r = new Random();
        auxiliar = listaPalabra[r.nextInt(listaPalabra.length)];
        auxiliar = auxiliar.toUpperCase();
        return auxiliar;
    }
}

