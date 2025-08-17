def main():
    nlines = 5
    for _ in range(nlines):
        i = nlines
        for _ in range(i):
            print('*', end='')
        nlines = (nlines - 1)
        print()

if __name__ == "__main__":
    main()
