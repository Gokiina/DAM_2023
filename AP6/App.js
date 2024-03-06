import React, {useEffect, useState} from 'react';
import {View, Text, TextInput} from 'react-native';

const App = () => {
  const letras = [
    'T',
    'R',
    'W',
    'A',
    'G',
    'M',
    'Y',
    'F',
    'P',
    'D',
    'X',
    'B',
    'N',
    'J',
    'Z',
    'S',
    'Q',
    'V',
    'H',
    'L',
    'C',
    'K',
    'E',
    'T',
  ];
  const [text, setText] = useState('Letra');
  const [nif, setNif] = useState('');
  const [color, setColor] = useState('blue');

  useEffect(() => {
    if (nif.length < 8) {
      setText('');
    }
    if (nif.length === 8) {
      const resto = parseInt(nif) % 23;
      const letra = letras[resto];
      setText(letra);
      setColor('green');
    }
  }, [nif]);

  return (
    <View style={{marginTop: 50, justifyContent: 'center'}}>
      <View style={{justifyContent: 'center'}}>
        <Text
          style={{
            alignContent: 'center',
            padding: 40,
            fontSize: 35,
            color: 'green',
          }}>
          Calculador Letra NIF
        </Text>
        <Text
          style={{
            alignContent: 'center',
            padding: 50,
            fontSize: 20,
            color: 'purple',
          }}>
          App para calcular la letra del NIF:
        </Text>
      </View>

      <View style={{flexDirection: 'row', justifyContent: 'center'}}>
        <TextInput
          style={{
            width: 170,
            padding: 10,
            fontSize: 30,
            color: 'red',
            borderBottomColor: 'red',
            borderBottomWidth: 1,
          }}
          placeholder="NIF"
          placeholderTextColor="red"
          onChangeText={newText => setNif(newText)}
          defaultValue={''}
          maxLength={8}
          keyboardType="numeric"
        />
        <Text
          style={{
            width: 100,
            color: color,
            padding: 10,
            fontSize: 30,
            borderBottomColor: 'blue',
            borderBottomWidth: 1,
          }}>
          {text}
        </Text>
      </View>
    </View>
  );
};

export default App;
