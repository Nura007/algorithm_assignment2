import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

df = pd.read_csv('benchmark_results.csv')
df['time(µs)'] = df['time(ns)'] / 1000.0

df_grouped = df.groupby(['n', 'version'])['time(µs)'].mean().reset_index()

plt.figure(figsize=(10, 6))

n_values = df_grouped['n'].unique()

for version in df_grouped['version'].unique():
    subset = df_grouped[df_grouped['version'] == version]
    plt.plot(
        subset['n'],
        subset['time(µs)'],
        label=version,
        marker='o',
        linewidth=2
    )

first_baseline_point = df_grouped[(df_grouped['n'] == 100) & (df_grouped['version'] == 'Baseline')]['time(µs)'].iloc[0]
o_n_reference = first_baseline_point * (n_values / 100)

plt.plot(
    n_values,
    o_n_reference,
    linestyle='--',
    color='gray',
    label=r'Теоретическая $O(n)$'
)

plt.xscale('log')
plt.yscale('log')
plt.xticks(n_values, labels=[f'{int(n):,}' for n in n_values])
plt.minorticks_off()

plt.title('Input size (n)', fontsize=16)
plt.xlabel('N (log scale)', fontsize=14)
plt.ylabel('Average time (µs) (log scale)', fontsize=14)

plt.text(
    n_values[0] * 1.2,
    plt.ylim()[0] * 1.5,
    r'Угол наклона $\approx 1$ на log-log графике подтверждает $O(n)$',
    color='black',
    fontsize=10
)

plt.grid(True, which="major", linestyle=':', linewidth=0.7)
plt.legend(title='Версия')
plt.tight_layout()

plt.savefig('complexity_verification_plot.png')
plt.show()