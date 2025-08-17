def main():
    r1 = ('A' == '\x41')
    r2 = ('A' == 'A')
    r3 = ('\x42' == 'A')
    print(r1, end='')
    print()
    print(r2, end='')
    print()
    print(r3, end='')
    print()

if __name__ == "__main__":
    main()
