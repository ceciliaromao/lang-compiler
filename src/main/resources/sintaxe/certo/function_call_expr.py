def f(x):
    y = ((2 * x) + 1)
    return y, 1.5

def main():
    z = 10
    h = ((2 * f(z)[0]) + f(z)[1])
    print(h, end='')

if __name__ == "__main__":
    main()
