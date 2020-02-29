
'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();


exports.sendFollowerNotification = functions.database.ref('main/diagnosis')
    .onWrite(async (change, context) => {
        
        var topic = 'diag';
        
       // var database=firebase.database

        const changediagnosis_Promise=admin.database()
       .ref('/main/changediagnosis').once('value');
        const changediagnosis = await Promise.all([changediagnosis_Promise]);
  


        const information_Promise = admin.database()
       .ref('/main/lastDiag').once('value');  

        const information= await Promise.all([information_Promise]);
  


        var message =  {
            notification: {
                title: '확진자 수가 '+changediagnosis[0].val()+'명 증가된 '+change.after.val()+'명 입니다',
                body: '(정보) ' +information[0].val()   
            },
            topic: topic
        };
       
        // Send a message to devices subscribed to the provided topic.
        admin.messaging().send(message)
          .then((response) => {
              // Response is a message ID string.
              console.log('Successfully sent message:', response);
              return null;
          })
          .catch((error) => {
              console.log('Error sending message:', error);
              return null;
          });

         
    });



exports.sendFollowerNotifications = functions.database.ref('map')//맵이 바꼇을떄
    .onWrite(async (change, context) => {


        var topic = 'map';

        var message = {
            notification: {
                title: '확진자 경로가 업데이트 되었습니다',
                body: `앱을 눌러 확인하세요`
            },
            topic: topic
        };
       
        // Send a message to devices subscribed to the provided topic.
        admin.messaging().send(message)
          .then((response) => {
              // Response is a message ID string.
              console.log('Successfully sent message:', response);
              return null;
          })
          .catch((error) => {
              console.log('Error sending message:', error);
              return null;
          });

         
    });


exports.sendFatalityNotifications = functions.database.ref('main/fatality')//사망자db 바꼇을떄 
    .onWrite(async (change, context) => {

        
        var topic = 'fatality';


        const fatality_Promise=admin.database()
     .ref('/main/info_fatality').once('value');
        const changedfatality_information = await Promise.all([fatality_Promise]);
  

        var message = {
            notification: {
                title: change.after.val()+'번째 사망자 발생',
                body: `(정보) `+changedfatality_information[0].val() 
            },
            topic: topic
        };
       
        // Send a message to devices subscribed to the provided topic.
        admin.messaging().send(message)
          .then((response) => {
              // Response is a message ID string.
              console.log('Successfully sent message:', response);
              return null;
          })
          .catch((error) => {
              console.log('Error sending message:', error);
              return null;
          });

         
    });
