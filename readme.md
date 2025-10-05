# 🚀 Kadane's Algorithm: Maximum Subarray Sum
# Nurtilek Kobylandy

## You can upload your analysis in /docs/(name_of_your_file).pdf

This repository focuses on the implementation and comprehensive analysis of **Kadane's Algorithm**—the optimal solution to the classic **Maximum Subarray Problem** in a one-dimensional array.

The project compares two versions: `Baseline` and `Optimized`, providing a detailed analysis of their asymptotic complexity and empirical performance validation.

---

## 🔬 Asymptotic Complexity Analysis

The algorithm achieves the best possible complexity for this problem by requiring only a single pass over the input array.

### 1. Time Complexity (Time Complexity) ⏱️

| Case | Notation | Description |
| :--- | :--- | :--- |
| **Best Case** | $\Theta(n)$ | Linear Time. |
| **Worst Case** | $\Theta(n)$ | Complexity is independent of data characteristics. |
| **Average Case** | $\Theta(n)$ | The fastest possible complexity. |

### 2. Space Complexity (Space Complexity) 💾

| Metric | Value | Description |
| :--- | :--- | :--- |
| **Auxiliary Space** | $O(1)$ | Uses a fixed number of variables to track current and maximum sums. |
| **In-place** | ✅ Yes | Does not require additional memory dependent on array size $n$. |

---

## 🛠️ Optimization and Code Review

The core optimization focused on **reducing the constant factor** ($c$ in the formula $T \approx c \cdot n$) to enhance real-world speed without changing the $O(n)$ complexity.

| Category | Goal | Result in Optimized Version |
| :--- | :--- | :--- |
| **Performance** | Minimizing operations inside the loop. | **Reduced constant factor**, maintaining $O(n)$. |
| **Edge Cases** | Clean handling of arrays with **all negative** elements. | Elimination of redundant checks; consistently returns the maximum single element. |
| **Code Quality** | Readability and reliability. | Improved logical structure and maintainability. |

---

## 📈 Empirical Performance Validation

Benchmarking was conducted across various input sizes to validate theoretical claims and measure the impact of optimizations.

### 1. Complexity Verification

* **Method:** A **Time vs. $n$** plot was generated using a **log-log scale**.
* **Result:** Both algorithm lines exhibit a **slope $\approx 1$**, **empirically confirming** the theoretical **$O(n)$** linear complexity.

### 2. Optimization Impact

| Input Size $n$ | Comparison | Conclusion |
| :--- | :--- | :--- |
| **$n = 100$** | Optimized vs. Baseline | **Optimized is 2-4 times faster** (on average). |
| **$n = 100,000$** | Optimized vs. Baseline | **Maintains a stable and substantial speed advantage.** |

**Conclusion:** The Optimized version is significantly faster than the Baseline in practice, successfully demonstrating the effectiveness of optimization focused on the constant factor.

---

## 📂 Repository Structure

| File / Directory        | Description |
|:------------------------| :--- |
| `src/main/java/...`     | 💻 Source code for the algorithm implementations. |
| `src/test/java/...`     | ✅ Unit tests for verification. |
| `benchmark_results.csv` | 📊 Raw benchmarking data. |
| `docs/NuraPlot.png`      | 🖼️ Final complexity verification plot. |
| `pom.xml`               | ⚙️ Maven configuration for building the project. |