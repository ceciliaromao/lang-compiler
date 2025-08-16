def main():
    r1 = ('A' == '\x41')
    r2 = ('A' == 'A')
    r3 = ('\x42' == 'A')
    print(r1, end='')
    print('\n', end='')
    print(r2, end='')
    print('\n', end='')
    print(r3, end='')
    print('\n', end='')

if __name__ == "__main__":
    main()
