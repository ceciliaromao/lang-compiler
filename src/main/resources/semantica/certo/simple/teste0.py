def main():
    nlines = 5
    i = nlines
    for _ in range(nlines):
        for _ in range(i):
            print('*', end='')
        i = (i - 1)
        print()

if __name__ == "__main__":
    main()
