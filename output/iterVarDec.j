.class public iterVarDec
.super java/lang/Object

.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    invokestatic iterVarDec/_main()V
    return
.end method

.method public static _main()V
    .limit stack 10
    .limit locals 10
    ldc 10
    istore 0
    ldc 21
    istore 1
    iconst_0
    istore 1
L0:
    iload 1
    iload 0
    ldc 2
    iadd
    if_icmpge L1
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 32
    invokevirtual java/io/PrintStream/print(C)V
    iinc 1 1
    goto L0
L1:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 46
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 10
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method


