const admin = require('firebase-admin')
const functions = require('firebase-functions');
admin.initializeApp(functions.config().firebase)
const fireStore = admin.firestore()

exports.calculateDisplayPercent = functions.https.onCall((data, context) => {
    const score = data.score;
    const bonus = data.bonus;
    fireStore
        .collection('scores')
        .get()
        .then(task => {
            const scoreSize = task.result.size();
            var counter = 0

            task.result.forEach(doc => {
                if (parseInt(doc.data["score"], 10) > score * bonus) {
                    counter++
                }
            });

            const percent = Math.ceil(counter / scoreSize * 100);
            var displayPercent;
            if (percent === 0) {
                return { displayPercent: 1 };
            } else {
                return { displayPercent: percent };
            }
        })
        .catch(err => {
            throw new functions.https.HttpsError('error', err);
        });
});