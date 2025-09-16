# BudgetPlannerApp — Simple Android Budget Tracker 💰

![Build Status](https://github.com/Qandil11/BudgetPlannerApp/actions/workflows/android-ci.yml/badge.svg)
![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)


A lightweight Android app to help you plan monthly budgets, track spending, and visualise expenses.  

---

## 🎯 Features

- Track income & expenses by category (Food, Transport, Bills, etc.)  
- Set monthly budget goals and monitor overspending  
- Simple charts to visualise your spending patterns  
- Export data via CSV for personal backups  

---

## 📸 Demo

<img width="800" height="1280" alt="Screenshot_20250713_155644" src="https://github.com/user-attachments/assets/d6e61905-2917-49e4-a58d-a8e9ae7c0117" />
<img width="800" height="1280" alt="Screenshot_20250713_155747" src="https://github.com/user-attachments/assets/86368dc8-b222-4743-8008-df6fa801a720" />
<img width="800" height="1280" alt="Screenshot_20250713_155801" src="https://github.com/user-attachments/assets/b8e1123a-eb9d-4d00-8b4e-86d2395a4fb5" />
<img width="800" height="1280" alt="Screenshot_20250713_155812" src="https://github.com/user-attachments/assets/6325c2dc-a4c6-4794-969d-c2156355d2b5" />


---

## 🚀 Why It Matters

Many users struggle to keep track of where their money goes each month.  
This app helps by simplifying budgeting on mobile, making financial planning accessible to everyone.  

---

## 🛠 Tech Stack

- Android (Kotlin)  
- MVVM architecture  
- Room or SQLite for local persistence  
- Jetpack Compose for UI  
- Gradle build + GitHub CI (automatic builds)

---

## ⚙️ Quickstart

```bash
git clone https://github.com/Qandil11/BudgetPlannerApp.git
cd BudgetPlannerApp

# Open in Android Studio
./gradlew :app:installDebug
```

---

## 🔧 Project Structure

```
BudgetPlannerApp/
 ├─ app/                  # Android UI & logic
 ├─ data/                 # Data persistence, models
 ├─ domain/               # Business logic
 ├─ assets/               # Screenshots, icons
 ├─ .github/ workflows     # (if CI present)
 └─ README.md
```

---

## 🆕 Roadmap

- [ ] Add recurring transactions  
- [ ] Add notifications for budget limit exceed  
- [ ] Add monthly summary export (PDF)  
- [ ] Sync across devices via cloud backup  

---

## 📌 License

MIT License  
