import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Image,
  TouchableOpacity,
  ScrollView,
  Dimensions,
} from 'react-native';

const screenWidth = Dimensions.get('window').width;

const images = [
  'https://images.unsplash.com/photo-1513721032312-6a18a42c8763?w=152&h=152&fit=crop&crop=faces',
  'https://images.unsplash.com/photo-1511765224389-37f0e77cf0eb?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1497445462247-4330a224fdb1?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1426604966848-d7adac402bff?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1502630859934-b3b41d18206c?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1515023115689-589c33041d3c?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1504214208698-ea1916a2195a?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1515814472071-4d632dbc5d4a?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1511407397940-d57f68e81203?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1518481612222-68bbe828ecd1?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1505058707965-09a4469a87e4?w=125&h=125&fit=crop',
  'https://images.unsplash.com/photo-1423012373122-fff0a5d28cc9?w=125&h=125&fit=crop',
];

const perfil = require('./images/perfil.png');
const posts = require('./images/posts.png');

export default class App extends Component {
  render() {
    return (
      <ScrollView style={{backgroundColor: 'white'}}>
        <View key="parte superior">
          <TouchableOpacity style={styles.containerTop}>
            <Image style={{width: 90, height: 90, borderRadius: 50, padding:20}} source={perfil} />
            <Image style={{width: screenWidth / 1.5, height: screenWidth / 4.5}} source={posts} />
          </TouchableOpacity>
        </View>
        <View key="cuadro con fotos" style={styles.container}>
          {images.map((image, index) => (
            <TouchableOpacity key={index}>
              <Image style={styles.image} source={{uri: image}} />
            </TouchableOpacity>
          ))}
        </View>
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  containerTop: {
    padding: 20,
    marginBottom: 10,
    marginTop: 30,
    flex: 1,
    justifyContent: 'space-between',
    backgroundColor: 'white',
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  container: {
    flex: 1,
    backgroundColor: 'white',
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  image: {
    borderWidth: 2,
    borderColor: 'white',
    width: screenWidth / 4,
    height: screenWidth / 4,
  },
});
