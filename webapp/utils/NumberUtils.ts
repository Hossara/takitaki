export const range = (start: number, stop: number, step: number = 1) => Array.from(
    { length: (stop - start) / step + 1 },
    (value, index) => start + index * step
)