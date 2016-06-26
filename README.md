RECONOCIMIENTO  FACIAL

El reconocimiento facial pasa por dos etapas, la primera es el aprendizaje, la segunda es el reconocimiento.

APLICACIÓN:

Se va a utilizar un dispositivo android para toda la aplicación.


Desarrollo de la aplicación RECONOCIMIENTO_FACIAL:

Se van a utilizar tres activities:

1. RECONOCIMIENTOFACIALActivity.java

Actividad principal, tiene el menú de la aplicación.

en esta activity se muestra la interfaz content_reconocimientofacial.xml

Se compone de tres botónes:

APRENDER: es para toma fotos de las personas que queremos reconocer(aprendizaje).

GRABAR: Graba el video que se va a utilizar en el reconocimiento.

RECONOCER: Reproduce el video y mientras se detectan las caras(reconocimiento).

2. AprenderActivity.java

se llama al darle click al botón APRENDER, aqui se carga la interfaz aprenderlayout.xml

3. ReconocerActivity.java

Se llama al darle Click al botón RECONOCER, aqui se carga la interfaz reconocerlayout.xml
	

