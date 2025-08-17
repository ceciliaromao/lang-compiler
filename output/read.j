.class public read
.super java/lang/Object

.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    invokestatic read/_main()V
    return
.end method

.method public static _main()V
    .limit stack 10
    .limit locals 10
    ldc 0
    istore 0
    new java/util/Scanner
    dup
    getstatic java/lang/System/in Ljava/io/InputStream;
    invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
    invokevirtual java/util/Scanner/nextLine()Ljava/lang/String;
    invokestatic java/lang/Integer/parseInt(Ljava/lang/String;)I
    istore 0
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 0
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method


