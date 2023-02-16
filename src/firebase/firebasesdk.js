
import firebase from 'firebase';

const firebaseConfig = {
  apiKey: "AIzaSyAesjudI70GiuP81j2EnSEW-b21uF0YVHg",
  authDomain: "logindemo-f818c.firebaseapp.com",
  projectId: "logindemo-f818c",
  storageBucket: "logindemo-f818c.appspot.com",
  messagingSenderId: "801054989324",
  appId: "1:801054989324:web:f622a93374f5ad13e52229",
  measurementId: "G-6J6CJDFL5V"
};

firebase.initializeApp(firebaseConfig);
const auth = firebase.auth();
export { auth, firebase };