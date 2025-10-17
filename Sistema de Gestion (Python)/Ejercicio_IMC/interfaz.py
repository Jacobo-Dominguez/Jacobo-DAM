from tkinter import *
from tkinter import ttk
from calculo import calcularIMC

ventana = Tk()
ventana.title("Calculadora de IMC")
ventana.geometry("300x300")
'''
El objetivo es construir una ventana que tome dos entradas (Peso y Altura) 
y muestre el resultado del cálculo del IMC al presionar un botón
'''
label_peso = ttk.Label(ventana, text="Peso (kg): ")
label_peso.pack()
entry_peso = ttk.Entry(ventana)
entry_peso.pack()

label_altura = ttk.Label(ventana, text="Altura (m): ")
label_altura.pack()
entry_altura = ttk.Entry(ventana)
entry_altura.pack()

label_resultado = ttk.Label(ventana, text="IMC: ")
label_resultado.pack()

button = ttk.Button(
    ventana,
    text="Calcular",
    command=lambda: calcularIMC(entry_peso, entry_altura, label_resultado)
)

button.pack()

ventana.mainloop()