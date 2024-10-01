import React, {Component} from 'react';
import {View, Text} from 'react-native';
import {CalculadoraIMC} from './components/CalculadoraIMC';

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      clasificacion: null,
    };
  }

  getIMC = ({clasificacion}) => {
    this.setState({clasificacion});
  };

  render() {
    return (
     <View style={{ backgroundColor: '#925faf', flex: 1, paddingTop: 70}}>
        <Text
          style={{
            color: 'white', textAlign: 'center', fontSize: 40, padding: 5}}>
          Calculadora IMC
        </Text>
        <CalculadoraIMC devuelveIMC={this.getIMC}></CalculadoraIMC>
        <Text style={{color: 'white', fontSize: 20, padding: 5}}>
          Created by Ana Rodriguez
        </Text>
      </View>
    );
  }
}
