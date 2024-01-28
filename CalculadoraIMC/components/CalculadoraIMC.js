import React, {Component} from 'react';
import {StyleSheet, View, Text} from 'react-native';
import { TextInput, Button } from 'react-native-paper';

export class CalculadoraIMC extends Component {
  constructor(props) {
    super(props);
    this.state = {
        peso: undefined,
        altura: undefined,
        colorIMC: 'white',
        clasificacion: '',
    };
  }

  calcularIMC = () => {
        let peso = this.state.peso;
        let altura = this.state.altura;
        let IMC = peso/(altura*altura);
        let clasificacion = '';

        if (IMC < 18.5){
          clasificacion = 'Peso insuficiente';
        } else if (IMC >= 18.5 && IMC < 25){
          clasificacion = 'Normopeso';
        } else if (IMC >= 25 && IMC < 27){
          clasificacion = 'Sobrepeso grado I';
        } else if (IMC >= 27 && IMC < 30){
          clasificacion = 'Sobrepeso grado II (preobesidad)';
        } else if (IMC >= 30 && IMC < 35){
          clasificacion = 'Obesidad de tipo I';
        } else if (IMC >= 35 && IMC < 40){
          clasificacion = 'Obesidad de tipo II';
        } else if (IMC >= 40 && IMC < 50){
          clasificacion = 'Obesidad de tipo III (mórbida)';
        } else if (IMC >= 50){
          clasificacion = 'Obesidad de tipo IV (extrema)';
        } else {
          clasificacion = 'error de cálculo';
        }
        let colorIMC = this.calculadoraColorIMC(IMC);
        this.setState({clasificacion, colorIMC});
        this.props.devuelveIMC(clasificacion);
  };

  calculadoraColorIMC = (IMC) =>{
    if (IMC < 27){
      return {color: 'green'};
    } else if (IMC >= 27 && IMC < 40){
      return {color: 'orange'};
    }else if (IMC >= 40){
      return {color: 'red'};
    }

  }

  render() {
    return (
      <View style={styles.contenedor}>
        <Text style={{color: 'red', fontSize: 40}}>Datos</Text>
        <View>
          <Text style={{color: 'blue', fontSize: 20, fontWeight: 'bold'}}>PESO</Text>
          <TextInput
            mode="outlined"
            label="Peso en kg"
            value={this.state.peso}
            onChangeText={peso => this.setState({ peso })}
            keyboardType="numeric"
          />
        </View>
        <View style={{padding: 15}}></View>
        <View>
          <Text style={{color: 'blue', fontSize: 20, fontWeight: 'bold'}}>ALTURA</Text>
          <TextInput
          mode="outlined"
            label="Altura en metros"
            value={this.state.altura}
            onChangeText={altura => this.setState({ altura })}
            keyboardType="numeric"
          />
        </View>
        <View style={{padding: 15}}>
          <Button mode="contained" onPress={this.calcularIMC}>Calcular IMC</Button>
        </View>
        <View>
            <Text style={{color: 'black', fontSize: 15}}>Resultado</Text>
            <Text style={this.state.colorIMC}>{this.state.clasificacion}</Text>
          </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  contenedor: {
    flexDirection: 'column',
    backgroundColor: 'white',
    borderWidth: 5,
    borderColor: 'rebeccapurple',
    padding: 20,
  },
});
