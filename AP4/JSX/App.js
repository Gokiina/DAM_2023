import React, {Component} from 'react';
import {View, Text, StyleSheet, ScrollView} from 'react-native';
import {TextInput, Button} from 'react-native-paper';

const estilo = 'upv';
const isAdmin = true;

const nombre = nombreRecibido => {
  return <Text>{nombreRecibido}</Text>;
};

const datos = datosRecibidos => {
  return (
    <View style={estilo === 'florida' ? styles.florida : styles.upv}>
      <TextInput
        label={datosRecibidos}
        placeholder="placeholder"
        placeholderTextColor={estilo === 'florida' ? 'white' : 'orange'}
        left={<TextInput.Icon icon="eye" />}
      />
      <TextInput
        label={datosRecibidos}
        placeholder="placeholder"
        placeholderTextColor={estilo === 'florida' ? 'white' : 'orange'}
        left={<TextInput.Icon icon="eye" />}
      />
    </View>
  );
};

const mostrarBotonInformes = () => {
  return (
    <Button mode="contained" icon="format-list-bulleted">
      INFORMES
    </Button>
  );
};

const modulos2Dam = [
  {nombre: 'DIN', profesor: 'Manel', horas: 120},
  {nombre: 'ADA', profesor: 'Juanmi', horas: 120},
  {nombre: 'PMDM', profesor: 'Fran', horas: 100},
  {nombre: 'PSP', profesor: 'Fran', horas: 60},
  {nombre: 'SGE', profesor: 'Belén', horas: 100},
  {nombre: 'Inglés', profesor: 'Caterina', horas: 40},
  {nombre: 'EIE', profesor: 'Manuel', horas: 60},
];

class App extends Component {
  render() {
    return (
      <View>
        {nombre('Ana Rodriguez')}
        {datos('Introduce tus datos')}
        {isAdmin && mostrarBotonInformes()}

        <ScrollView>
          {modulos2Dam.map((item, pos) => {
            return (
              <View
                key={pos}
                style={pos % 2 === 0 ? styles.pares : styles.impares}>
                <Text>{pos + 1}</Text>
                <Text>{item.nombre}</Text>
                <Text>{item.profesor}</Text>
                <Text>{item.horas}</Text>
              </View>
            );
          })}
        </ScrollView>
        
      </View>
    );
  }
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  upv: {
    backgroundColor: 'purple',
    fontSize: 10,
    fontWeight: '600',
    padding: 4,
    paddingLeft: 12,
    textAlign: 'left',
    color: 'grey',
  },
  florida: {
    backgroundColor: 'red',
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
  impares: {
    backgroundColor: '#F8BBD0',
  },
  pares: {
    backgroundColor: '#F48FB1',
  },
});

export default App;
