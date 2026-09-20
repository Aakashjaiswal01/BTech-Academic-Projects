import streamlit as st
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

st.set_page_config(page_title="Smart ATS Resume Screener", layout="centered")
st.title("🤖 AI-Assisted ATS Resume Screener")
st.subheader("B.Tech CSE (AI/ML) Academic Project")
st.write("This application uses Natural Language Processing (NLP) to calculate the match percentage between a Job Description and a Candidate's Resume.")
st.divider()

st.write("### 📄 Step 1: Paste the Job Description")
job_description = st.text_area("Paste the job requirements here...", height=150)
st.write("### 👤 Step 2: Paste the Resume Text")
resume_text = st.text_area("Paste your resume raw text here...", height=150)
st.divider()

if st.button("🚀 Calculate Match Score"):
    if job_description and resume_text:
        with st.spinner("Analyzing text patterns..."):
            text_data = [job_description, resume_text]
            vectorizer = TfidfVectorizer(stop_words='english')
            tfidf_matrix = vectorizer.fit_transform(text_data)
            similarity_matrix = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:2])
            match_percentage = round(similarity_matrix[0][0] * 100, 2)
            
            st.success("### Analysis Complete!")
            if match_percentage >= 70:
                st.balloons()
                st.metric(label="ATS Match Score", value=f"{match_percentage}%", delta="Excellent Match for this Role!")
            elif match_percentage >= 40:
                st.metric(label="ATS Match Score", value=f"{match_percentage}%", delta="Average Match - Needs Optimization", delta_color="off")
            else:
                st.metric(label="ATS Match Score", value=f"{match_percentage}%", delta="- Low Match: Missing Core Keywords", delta_color="inverse")
    else:
        st.error("⚠️ Please paste both the Job Description and the Resume Text to calculate the score.")