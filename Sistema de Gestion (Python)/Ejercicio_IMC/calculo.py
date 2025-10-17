def calcularIMC(entry_peso, entry_altura, label_resultado):
    try:
        peso = float(entry_peso.get())
        altura = float(entry_altura.get())
        imc = peso / (altura * altura)
        label_resultado.config(text=f"IMC: {imc:.2f}")
    except ValueError:
        label_resultado.config(text=f"El valor de la entrada es incorrecto")