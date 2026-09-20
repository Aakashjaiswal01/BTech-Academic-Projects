import streamlit as st
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score

st.set_page_config(page_title="Customer Churn Predictor", layout="centered")
st.title("📊 Customer Churn ML Predictor")
st.subheader("B.Tech CSE (AI/ML) Core Project")
st.write("This application trains a Random Forest Classifier in real-time to predict whether a customer will cancel their subscription.")

st.divider()

# 1. Create a dummy dataset on the fly for ease of use
@st.cache_data
def load_data():
    data = {
        'Usage_Hours': [10, 50, 5, 80, 12, 90, 8, 75, 15, 85] * 10,
        'Support_Calls': [4, 1, 5, 0, 4, 0, 5, 1, 3, 0] * 10,
        'Monthly_Bill': [80, 30, 95, 20, 85, 25, 90, 35, 70, 40] * 10,
        'Churn': [1, 0, 1, 0, 1, 0, 1, 0, 1, 0] * 10 # 1 = Left company, 0 = Stayed
    }
    return pd.DataFrame(data)

df = load_data()

st.write("### 📈 Training Data Insights (Sample Matrix)")
st.dataframe(df.head(6))

# 2. Train the Machine Learning Model
X = df[['Usage_Hours', 'Support_Calls', 'Monthly_Bill']]
y = df['Churn']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

model = RandomForestClassifier()
model.fit(X_train, y_train)
predictions = model.predict(X_test)
accuracy = accuracy_score(y_test, predictions) * 100

st.sidebar.markdown("### 🤖 Model Statistics")
st.sidebar.metric(label="Model Accuracy", value=f"{int(accuracy)}%")

st.divider()

# 3. User Input Fields for Prediction
st.write("### 🔮 Predict Churn for a Specific Customer")
usage = st.slider("Monthly Usage Hours", 1, 100, 30)
calls = st.slider("Customer Support Calls This Month", 0, 10, 2)
bill = st.slider("Monthly Bill Amount ($)", 10, 150, 50)

if st.button("🧠 Run ML Prediction"):
    user_data = pd.DataFrame([[usage, calls, bill]], columns=['Usage_Hours', 'Support_Calls', 'Monthly_Bill'])
    prediction = model.predict(user_data)
    
    st.write("### 🚨 Prediction Result:")
    if prediction[0] == 1:
        st.error("⚠️ High Risk Candidate: The ML model predicts this customer is highly likely to CHURN (leave the company).")
    else:
        st.success("✅ Loyal Customer: The ML model predicts this customer will STAY with the company.")