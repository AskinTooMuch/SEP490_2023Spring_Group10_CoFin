import firebase from 'firebase/compat/app';
import 'firebase/compat/auth';
import 'firebase/compat/firestore';
// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyB9uad0J5NPig57xGUmSXhukN9Dc17Bals",
  authDomain: "sep490-f8455.firebaseapp.com",
  projectId: "sep490-f8455",
  storageBucket: "sep490-f8455.appspot.com",
  messagingSenderId: "774956465668",
  appId: "1:774956465668:web:5676108c145f9e1ca97f51",
  measurementId: "G-J7Q7S8K8TB"
};
firebase.initializeApp(firebaseConfig);
const auth = firebase.auth();
export { auth, firebase };